package pt.up.fe.ddsfl.instrumenter.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import flexjson.JSON;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import javassist.Modifier;
import pt.up.fe.ddsfl.annotations.dispatchers.Dispatcher;
import pt.up.fe.ddsfl.annotations.handlers.LandmarkHandler;
import pt.up.fe.ddsfl.common.events.EventListener;
import pt.up.fe.ddsfl.common.events.NullEventListener;
import pt.up.fe.ddsfl.common.messaging.Client;
import pt.up.fe.ddsfl.instrumenter.granularity.GranularityFactory.GranularityLevel;
import pt.up.fe.ddsfl.instrumenter.matchers.BlackList;
import pt.up.fe.ddsfl.instrumenter.matchers.ClassNamesMatcher;
import pt.up.fe.ddsfl.instrumenter.matchers.DuplicateCollectorReferenceMatcher;
import pt.up.fe.ddsfl.instrumenter.matchers.FieldNameMatcher;
import pt.up.fe.ddsfl.instrumenter.matchers.InterfaceMatcher;
import pt.up.fe.ddsfl.instrumenter.matchers.Matcher;
import pt.up.fe.ddsfl.instrumenter.matchers.ModifierMatcher;
import pt.up.fe.ddsfl.instrumenter.matchers.OrMatcher;
import pt.up.fe.ddsfl.instrumenter.matchers.PrefixMatcher;
import pt.up.fe.ddsfl.instrumenter.matchers.WhiteList;
import pt.up.fe.ddsfl.instrumenter.passes.FilterPass;
import pt.up.fe.ddsfl.instrumenter.passes.InstrumentationPass;
import pt.up.fe.ddsfl.instrumenter.passes.LandmarkInserterPass;
import pt.up.fe.ddsfl.instrumenter.passes.ParameterDataPass;
import pt.up.fe.ddsfl.instrumenter.passes.Pass;
import pt.up.fe.ddsfl.instrumenter.passes.Pass.Outcome;
import pt.up.fe.ddsfl.instrumenter.passes.StackSizePass;
import pt.up.fe.ddsfl.instrumenter.passes.TestFilterPass;
import pt.up.fe.ddsfl.instrumenter.passes.VectorInitializationPass;

public class AgentConfigs {

    private int port = -1;
    private GranularityLevel granularityLevel = GranularityLevel.method;
    private List<String> prefixesToFilter = new ArrayList<String>();

    private List<Pass> passesToPrepend = new ArrayList<Pass>();
    private ClassNamesMatcher classNamesMatcher = new ClassNamesMatcher();
    private boolean filterClassNames = false;

    private boolean instrumentLandmarks = false;
    private boolean instrumentParameters = false;

    private int maxValueProbes = 10000;

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setFilterClassNames(boolean filter) {
        this.filterClassNames = filter;
    }

    public boolean getFilterClassNames() {
        return filterClassNames;
    }

    public void setInstrumentLandmarks(boolean instrumentLandmarks) {
        this.instrumentLandmarks = instrumentLandmarks;
    }

    public boolean getInstrumentLandmarks() {
        return instrumentLandmarks;
    }

    public void setInstrumentParameters(boolean instrumentParameters) {
        this.instrumentParameters = instrumentParameters;
    }

    public boolean getInstrumentParameters() {
        return instrumentParameters;
    }

    @JSON(include = false)
    public ClassNamesMatcher getClassNamesMatcher() {
        return this.classNamesMatcher;
    }

    public GranularityLevel getGranularityLevel() {
        return granularityLevel;
    }

    public void setGranularityLevel(GranularityLevel granularityLevel) {
        this.granularityLevel = granularityLevel;
    }

    public List<String> getPrefixesToFilter() {
        return prefixesToFilter;
    }

    public void setPrefixesToFilter(List<String> prefixesToFilter) {
        this.prefixesToFilter = prefixesToFilter;
    }

    public void addPrefixToFilter(String... prefixes) {
        for (String prefix : prefixes) {
            this.prefixesToFilter.add(prefix);
        }
    }

    public void prependPass(Pass pass) {
        passesToPrepend.add(pass);
    }

    @JSON(include = false)
    public List<Pass> getInstrumentationPasses() {
        List<Pass> instrumentationPasses = new ArrayList<Pass>();

        // Ignores classes in particular packages
        List<String> prefixes = new ArrayList<String>();
        Collections.addAll(prefixes, "javax.", "java.", "sun.", "com.sun.", "pt.up.fe.ddsfl.");
        prefixes.addAll(prefixesToFilter);

        PrefixMatcher pMatcher = new PrefixMatcher(prefixes);

        Matcher mMatcher = new OrMatcher(new ModifierMatcher(Modifier.NATIVE), new ModifierMatcher(Modifier.INTERFACE));

        Matcher landmarkAspects = new OrMatcher (new InterfaceMatcher(LandmarkHandler.class.getName()),
                new InterfaceMatcher(Dispatcher.class.getName()));

        Matcher alreadyInstrumented = new OrMatcher(new FieldNameMatcher(InstrumentationPass.HIT_VECTOR_NAME),
                new DuplicateCollectorReferenceMatcher());

        FilterPass fp = new FilterPass(new BlackList(mMatcher),
                new BlackList(pMatcher),
                new BlackList(landmarkAspects),
                new BlackList(alreadyInstrumented));

        FilterPass classFiltering = new FilterPass(new WhiteList(classNamesMatcher));
        classFiltering.setFallbackOutcome(filterClassNames ? Outcome.CANCEL : Outcome.CONTINUE);

        instrumentationPasses.addAll(passesToPrepend);
        instrumentationPasses.add(classFiltering);
        instrumentationPasses.add(fp);
        instrumentationPasses.add(new TestFilterPass());
        instrumentationPasses.add(new InstrumentationPass(granularityLevel));
        instrumentationPasses.add(new StackSizePass());

        if (instrumentLandmarks) {
            instrumentationPasses.add(new LandmarkInserterPass());
            instrumentationPasses.add(new StackSizePass());
        } else if (instrumentParameters) {
            instrumentationPasses.add(new ParameterDataPass());
            instrumentationPasses.add(new StackSizePass());
        }

        instrumentationPasses.add(new VectorInitializationPass());
        instrumentationPasses.add(new StackSizePass());

        return instrumentationPasses;
    }

    @JSON(include = false)
    public EventListener getEventListener() {
        if (getPort() != -1) {
            return new Client(getPort());
        } else {
            return new NullEventListener();
        }
    }

    public String serialize() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

    public static AgentConfigs deserialize(String str) {
        try {
            return new JSONDeserializer<AgentConfigs>().deserialize(str, AgentConfigs.class);
        } catch (Throwable t) {
            return null;
        }
    }

    public int getMaxValueProbes() {
        return maxValueProbes;
    }

    public void setMaxValueProbes(int maxProbes) {
        this.maxValueProbes = maxProbes;
    }
}

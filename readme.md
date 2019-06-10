# Q-SFL

A qualitative-reasoning-based extension to SFL. This project provides a
framework for partitioning method parameters and return types according to their
runtime value as a way to mitigate _ambiguity grouping_ and _coincidental
correctness_. Based upon work published at
[IJCAI-ECAI'18](https://www.ijcai.org/proceedings/2018/267).

## Compilation and Installation

To compile the project and install the project in your local maven repository,
simply run the command:
```
mvn install
```

## Usage

Add the following to a Java project's `pom.xml`:
```
<build>
  <pluginManagement>
    <plugins>
      ...
      <plugin>
        <groupId>pt.up.fe.qsfl</groupId>
        <artifactId>qsfl-maven-plugin</artifactId>
        <version>0.1-SNAPSHOT</version>
        <configuration>
          <valueProbes>false</valueProbes>
        </configuration>
      </plugin>
      ...
    </plugins>
  </pluginManagement>
</build>
```

To run the project's test cases and perform the Q-SFL instrumentation, execute
the command:
```
mvn qsfl:test
```

For an usage example, please refer to
[aperez/q-sfl-example](https://github.com/aperez/q-sfl-example).

#### Value Probes

If the `valueProbes` configuration parameter of Q-SFL is enabled, parameter
values for function invocations will be recorded.

## Caveats

#### Junit3 and Junit4 Compatibility

Although `qsfl-maven-plugin` works for both Junit3 and Junit4 test cases, please
make sure that the Junit dependency included in the project's `pom.xml` is at
least **version 4.6**.

This requirement is due to the fact that Junit only provides their
`org.junit.runner.notification.RunListener` API after version 4.6. The listener
API is used so that per-test coverage can be gathered.  For Junit3 test cases,
the appropriate test runner will still be used.

Currently, this plugin also does not support test execution forking.

gradle-plugins
==============

my gradle plugins

this project provides some plugins for Java web developer.

### Maven Repository

add dependency to buildscript
```xml
        <dependency>
            <groupId>me.xyzlast</groupId>
            <artifactId>gradle-plugins</artifactId>
            <version>0.2.0-RELEASE</version>
        </dependency>
```

add repository to buildscript
```xml
    <repositories>
        <repository>
            <id>xyzlast-releases</id>
            <url>https://github.com/xyzlast/xyzlast-maven-repo/raw/master/releases</url>
        </repository>
    </repositories>
```


## local-resource

Supply the environment that each developers have each resources directory include hostname.

```groovy
apply plugin : 'local-resource'
```

If you want to running with the specification environment, please add parameter target.

```
gradle compileJava -Ptarget=dev // this target running with resources and resources-dev directory.
```

## querydsl

generate Q-classes for queryDSL. supply ```generateQueryDSL``` task or compileJava dependsOn.

```groovy
apply plugin : 'querydsl'
```

## bower

supply ```bowerInstall``` task. this task will copy dist js sources defined in 'bower.js'.

sample bower.js
```
{
    "name" : "bookstore-web",
    "version" : "0.0.0.1",
    "dependencies" : {
        "jquery" : "1.11.0",
        "bootstrap" : "3.1.1"
    },
    "install" : {
        "path" : {
            "css" : "src/main/webapp/lib/css",
            "js" : "src/main/webapp/lib/js",
            "eot" : "src/main/webapp/lib/fonts",
            "svg" : "src/main/webapp/lib/fonts",
            "ttf" : "src/main/webapp/lib/fonts",
            "woff" : "src/main/webapp/lib/fonts",
            "map" : "src/main/webapp/lib/js"
        },
        "sources" : {
            "jquery" : [
                    "bower_components/jquery/dist/jquery.min.js",
                    "bower_components/jquery/dist/jquery.min.map"
                ],
            "bootstrap" : [
                    "bower_components/bootstrap/dist/css/bootstrap.min.css",
                    "bower_components/bootstrap/dist/css/bootstrap-theme.min.css",
                    "bower_components/bootstrap/dist/fonts/glyphicons-halflings-regular.eot",
                    "bower_components/bootstrap/dist/fonts/glyphicons-halflings-regular.svg",
                    "bower_components/bootstrap/dist/fonts/glyphicons-halflings-regular.ttf",
                    "bower_components/bootstrap/dist/fonts/glyphicons-halflings-regular.woff",
                    "bower_components/bootstrap/dist/js/bootstrap.min.js"
                ]
        }
    }
}

```

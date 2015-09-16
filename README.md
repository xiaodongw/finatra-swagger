# swagger-finatra
Add Swagger support for Finatra web framework.

# Getting started
## Gradle
Add repository

	repositories {
	  maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
	}

Add Dependency

Scala 2.10, Finatra 1.6.0

	compile "com.github.xiaodongw:swagger-finatra_2.10:0.1.0-SNAPSHOT"

Scala 2.10, Finatra 2.0.0.M1

	compile "com.github.xiaodongw:swagger-finatra2_2.10:0.1.0-SNAPSHOT"

Scala 2.11, Finatra 2.0.0.M1

	compile "com.github.xiaodongw:swagger-finatra2_2.11:0.2.0-SNAPSHOT"
	
Scala 2.11, Finatra 2.0.0

	compile "com.github.xiaodongw:swagger-finatra2_2.11:0.2.1-SNAPSHOT"


## SBT
	resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

Add Dependency

Finatra 1.6.0

	libraryDependencies += "com.github.xiaodongw" %% "swagger-finatra" % "0.1.0-SNAPSHOT"

Finatra 2.0.0.M1

    libraryDependencies += "com.github.xiaodongw" %% "swagger-finatra2" % "0.2.0-SNAPSHOT"
    
Finatra 2.0.0
    
    libraryDependencies += "com.github.xiaodongw" %% "swagger-finatra2" % "0.2.1-SNAPSHOT"


## Add document information for you controller

    class SampleController extends Controller with SwaggerSupport {
      get("/students/:id",
        swagger { o =>
          o.summary("Read the detail information about the student")
          o.tags("Student")
          o.routeParam[String]("id", "the student id")
          o.response[Student](200, "the student details")
          o.response(404, "the student is not found")
        }) { request =>
        ...
      }

## Add document controller

    object SampleApp extends FinatraServer {
      FinatraSwagger.registerInfo(
        description = "The Student / Course management API, this is a sample for swagger document generation",
        version = "1.0.1",
        title = "Student / Course Management API")
    
      register(new SwaggerController())
      ...
    }

Now, check your shining API documents at 

Swagger API document: ```http://localhost:7070/api-docs```

Swagger UI: ```http://localhost:7070/api-docs/ui```
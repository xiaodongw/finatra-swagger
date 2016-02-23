package com.github.xiaodongw.swagger.finatra

import java.util.Date

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import org.joda.time.{DateTime, LocalDate}

class SampleController extends Controller with SwaggerSupport {
  override val finatraSwagger: FinatraSwagger = SampleSwagger

  case class HelloResponse(text: String, time: Date)

  get("/students/:id",
    swagger { o =>
      o.summary("Read student information")
      o.description("Read the detail information about the student.")
      o.tags("Student")
      o.routeParam[String]("id", "the student id")
      o.produces("application/json")
      o.response[Student](200, "the student object",
        example = Some(Student("Tom", "Wang", Gender.Male, new LocalDate(), 4, Some(Address("California Street", "94111")))))
      o.response(404, "the student is not found")
    }) { request: Request =>
    val id = request.getParam("id")

    response.ok.json(Student("Alice", "Wang", Gender.Female, new LocalDate(), 4, Some(Address("California Street", "94111")))).toFuture
  }

  post("/students",
    swagger { o =>
      o.summary("Create a new student")
      o.tags("Student")
      o.bodyParam[Student]("student", "the student details")
      o.response(200, "the student is created")
      o.response(500, "internal error")
    }) { student: Student =>
    //val student = request.contentString
    response.ok.json(student).toFuture
  }

  put("/students/:id",
    swagger { o =>
      o.summary("Update the student")
      o.tags("Student")
      o.formParam[String]("name", "the student name")
      o.formParam[Int]("grade", "the student grade")
      o.routeParam[String]("id", "student ID")
      o.cookieParam[String]("who", "who make the update")
      o.headerParam[String]("token", "the token")
      o.response(200, "the student is updated")
      o.response(404, "the student is not found")
    }) { request: Request =>
    val id = request.getParam("id")
    val name = request.getParam("name")
    val grade = request.getIntParam("grade")
    val who = request.cookies.getOrElse("who", "Sam")  //todo swagger-ui not set the cookie?
    val token = request.headerMap("token")

    response.ok.toFuture
  }

  get("/students",
    swagger { o =>
      o.summary("Get a list of students")
      o.tags("Student")
      o.response[Array[String]](200, "the student ids")
      o.response(500, "internal error")
      o.addSecurity("sampleBasic", List())
    }) { request: Request =>
    response.ok.json(Array("student1", "student2")).toFuture
  }

  get("/courses",
    swagger { o =>
      o.summary("Get a list of courses")
      o.tags("Course")
      o.response[Array[String]](200, "the courses ids")
      o.response(500, "internal error")
    }) { request: Request =>
    response.ok.json(Array("course1", "course2")).toFuture
  }

  get("/courses/:id",
    swagger { o =>
      o.summary("Get the detail of a course")
      o.tags("Course")
      o.routeParam[String]("id", "the course id")
      o.response[Course](200, "the courses detail")
      o.response(500, "internal error")
    }) { request: Request =>
    response.ok.json(Course(new DateTime(), "calculation", Seq("math"), CourseType.LAB, 20, BigDecimal(300.54))).toFuture
  }

  get("/courses/:courseId/student/:studentId",
    swagger { o =>
      o.summary("Is the student in this course")
      o.tags("Course", "Student")
      o.routeParam[String]("courseId", "the course id")
      o.routeParam[String]("studentId", "the student id")
      o.response[Boolean](200, "true / false")
      o.response(500, "internal error")
      o.deprecated(true)
    }) { request: Request =>
    response.ok.json(true).toFuture
  }
}

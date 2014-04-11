organization := "org.scalanlp"

// lazy val breeze = project in file("core")
name := "breeze-natives"

scalaVersion := "2.10.3"

addCompilerPlugin("org.scala-lang.plugins" % "macro-paradise" % "2.0.0-SNAPSHOT" cross CrossVersion.full)

publishMavenStyle := true

publishTo <<= version { (v: String) =>
  if (v.trim.endsWith("SNAPSHOT"))
    Some("tresata-snapshots" at "http://server01:8080/archiva/repository/snapshots")
  else
    Some("tresata-releases"  at "http://server01:8080/archiva/repository/internal")
}

credentials += Credentials(Path.userHome / ".m2" / "credentials_internal")

credentials += Credentials(Path.userHome / ".m2" / "credentials_snapshots")

credentials += Credentials(Path.userHome / ".m2" / "credentials_proxy")

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>http://scalanlp.org/</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:dlwh/breeze.git</url>
    <connection>scm:git:git@github.com:dlwh/breeze.git</connection>
  </scm>
  <developers>
    <developer>
      <id>dlwh</id>
      <name>David Hall</name>
      <url>http://cs.berkeley.edu/~dlwh/</url>
    </developer>
  </developers>)

scalacOptions ++= Seq("-deprecation","-language:_")

// scalacOptions in (Compile, console) += "-Xlog-implicits"


  javacOptions ++= Seq("-target", "1.6", "-source","1.6")


libraryDependencies ++= Seq(
  "com.github.fommil.netlib" % "all" % "1.1.2" pomOnly()
  )

// see https://github.com/typesafehub/scalalogging/issues/23
testOptions in Test += Tests.Setup(classLoader =>
  classLoader
    .loadClass("org.slf4j.LoggerFactory")
    .getMethod("getLogger", classLoader.loadClass("java.lang.String"))
    .invoke(null, "ROOT")
)

resolvers ++= Seq(
    Resolver.mavenLocal,
    Resolver.sonatypeRepo("snapshots"),
    Resolver.sonatypeRepo("releases"),
    Resolver.typesafeRepo("releases")
    )

testOptions in Test += Tests.Argument("-oDF")

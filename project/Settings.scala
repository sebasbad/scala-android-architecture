/*
 *  Copyright (C) 2016 47 Degrees, LLC http://47deg.com hello@47deg.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may obtain
 *  a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import Libraries.android._
import Libraries.cats._
import Libraries.json._
import Libraries.macroid._
import Libraries.monix._
import Libraries.test._
import Libraries.graphics._
import android.Keys._
import sbt.Keys._
import sbt._

object Settings {

  // Android Module
  lazy val androidAppSettings = basicSettings ++
    Seq(
      name := "scala-android-architecture",
      platformTarget in Android := "android-23",
      run <<= run in Android,
      ivyScala := ivyScala.value map {
        _.copy(overrideScalaVersion = true)
      },
      javacOptions in Compile ++= Seq("-target", "1.7", "-source", "1.7"),
      transitiveAndroidLibs in Android := true,
      libraryDependencies ++= androidDependencies,
      packagingOptions in Android := PackagingOptions(
        Seq("META-INF/LICENSE",
          "META-INF/LICENSE.txt",
          "META-INF/NOTICE",
          "META-INF/NOTICE.txt")),
      dexMaxHeap in Android := "2048m",
      proguardScala in Android := true,
      useProguard in Android := true,
      proguardCache in Android := Seq.empty,
      proguardOptions in Android ++= proguardCommons,
      dexMulti in Android := true)

  // Services Module
  lazy val servicesSettings = basicSettings ++ librarySettings

  // Process Module
  lazy val jobsSettings = basicSettings ++ librarySettings

  // Sarch Module
  lazy val sarchSettings = basicSettings ++ librarySettings

  lazy val androidDependencies = Seq(
    aar(macroidRoot),
    aar(androidSupportv4),
    aar(androidAppCompat),
    aar(androidDesign),
    aar(androidCardView),
    aar(androidRecyclerview),
    aar(macroidExtras),
    playJson,
    picasso,
    specs2,
    mockito,
    androidTest)

  // Basic Setting for all modules
  lazy val basicSettings = Seq(
    scalaVersion := Versions.scalaV,
    resolvers ++= commonResolvers,
    libraryDependencies ++= Seq(cats, monixTypes, monixEval)
  )

  lazy val duplicatedFiles = Set("AndroidManifest.xml")

  // Settings associated to library modules
  lazy val librarySettings = Seq(
    mappings in(Compile, packageBin) ~= {
      _.filter { tuple =>
        !duplicatedFiles.contains(tuple._1.getName)
      }
    },
    exportJars := true,
    scalacOptions in Compile ++= Seq("-deprecation", "-Xexperimental"),
    javacOptions in Compile ++= Seq("-target", "1.7", "-source", "1.7"),
    javacOptions in Compile += "-deprecation",
    proguardScala in Android := false)

  lazy val commonResolvers =
    Seq(
        Resolver.mavenLocal,
        DefaultMavenRepository,
        Resolver.typesafeRepo("releases"),
        Resolver.typesafeRepo("snapshots"),
        Resolver.typesafeIvyRepo("snapshots"),
        Resolver.sonatypeRepo("releases"),
        Resolver.sonatypeRepo("snapshots"),
        Resolver.defaultLocal,
        Resolver.jcenterRepo,
        "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
      )

  lazy val proguardCommons = Seq(
    "-ignorewarnings",
    "-keep class scala.Dynamic",
    "-keep class com.fortysevendeg.scala.android.** { *; }",
    "-keep class macroid.** { *; }",
    "-keep class android.** { *; }")

}

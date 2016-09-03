## The `io.github.cite_architecture.cite` library

This is a library, written in Scala, for working with citable objects.

## Why ?

It duplicates much of the functionality of the existing jvm library `edu.harvard.chs.cite`:  why bother?  Here are a few reasons.

**More idiomatic syntax when used in Scala code**.  A few examples: constructor without `new`:

    val urn = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:1.1")

function or property values accessible with simple member notation:

    urn.namespace == "greekLit" // expression is true


**Type-safe data handling**.  E.g., since `CtsUrn` and `CiteUrn` are case classes  extending the `Urn` superclass, you can pattern match on them:

    def idString (urn: Urn) = {
      urn match {
        case u: CtsUrn => "It's a CTS URN! " + u
        case _ => "Beats me."
      }
    }


**New features**.  For example, the `Citable` trait:  guarantee that any class you write extending this trait can be identified by a URN value -- potentially, a CitableGraph, an Orca object, or any object model you want to be citable (a Codex?).

**Cross-compilation for use in javascript "VM"s**.  Unlike the JVM library, this scala source could be compiled either for the JVM or (using scalajs) to javascript.  (NB: I have not yet added scalajs compilation to `build.sbt`.)

## Notes on the current state of the library

Currently, the library includes an initial implementation of the `CtsUrn` class.  

It uses `sbt` for its build system.  It is currently configured for testing with `specs2`.  Run all tests with `sbt test`.

It includes support for the Ammonite shell.  If you start the shell with `sbt test:console`, it will be able to resolve all the dependencies  defined in  your `build.sbt`.  You can either import the `cite` library at the ammonite prompt, or add the import statement to your `predef.sc` file (in `$HOME/.ammonite`): in either case,

    import io.github.cite_architecture.cite._



## Using the library locally in ammonite scripts

tba

# Stanford CoreNLP XML Server

[![Build Status](https://travis-ci.org/nlohmann/StanfordCoreNLPXMLServer.png)](https://travis-ci.org/nlohmann/StanfordCoreNLPXMLServer)

## About

This software offers the functionality of the [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml) as HTTP-XML-Server. This avoids the time-consuming initialization every time CoreNLP is started. It is very similar to projects like [this Python wrapper](https://github.com/relwell/stanford-corenlp-python).

## Example

The server will be listening at <http://localhost:8080>. The text you want to analyze needs to be POSTed as field `text`:

     curl --data 'text=Hello world!' http://localhost:8080

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet href="CoreNLP-to-HTML.xsl" type="text/xsl"?>
<root>
  <document>
    <sentences>
      <sentence id="1">
        <tokens>
          <token id="1">
            <word>Hello</word>
            <lemma>hello</lemma>
            <CharacterOffsetBegin>0</CharacterOffsetBegin>
            <CharacterOffsetEnd>5</CharacterOffsetEnd>
            <POS>UH</POS>
            <NER>O</NER>
          </token>
          <token id="2">
            <word>world</word>
            <lemma>world</lemma>
            <CharacterOffsetBegin>6</CharacterOffsetBegin>
            <CharacterOffsetEnd>11</CharacterOffsetEnd>
            <POS>NN</POS>
            <NER>O</NER>
          </token>
          <token id="3">
            <word>!</word>
            <lemma>!</lemma>
            <CharacterOffsetBegin>11</CharacterOffsetBegin>
            <CharacterOffsetEnd>12</CharacterOffsetEnd>
            <POS>.</POS>
            <NER>O</NER>
          </token>
        </tokens>
        <parse>(ROOT (S (VP (NP (INTJ (UH Hello)) (NP (NN world)))) (. !))) </parse>
        <dependencies type="basic-dependencies">
          <dep type="root">
            <governor idx="0">ROOT</governor>
            <dependent idx="2">world</dependent>
          </dep>
          <dep type="discourse">
            <governor idx="2">world</governor>
            <dependent idx="1">Hello</dependent>
          </dep>
        </dependencies>
        <dependencies type="collapsed-dependencies">
          <dep type="root">
            <governor idx="0">ROOT</governor>
            <dependent idx="2">world</dependent>
          </dep>
          <dep type="discourse">
            <governor idx="2">world</governor>
            <dependent idx="1">Hello</dependent>
          </dep>
        </dependencies>
        <dependencies type="collapsed-ccprocessed-dependencies">
          <dep type="root">
            <governor idx="0">ROOT</governor>
            <dependent idx="2">world</dependent>
          </dep>
          <dep type="discourse">
            <governor idx="2">world</governor>
            <dependent idx="1">Hello</dependent>
          </dep>
        </dependencies>
      </sentence>
    </sentences>
  </document>
</root>
```

## Installation

1. Clone the repository:
    
        git clone https://github.com/nlohmann/StanfordCoreNLPXMLServer.git

2. Download and install the third party libraries:
    
        cd StanfordCoreNLPXMLServer
        ant libs

3. Compile the JAR file:

        ant jar

4. Run the server:

        ant run

5. The server is now waiting on <http://localhost:8080> for HTTP POST requests. Note the initialization can take a few minutes, because several modules and resources of Stanford CoreNLP need to be loaded.

## Prerequisites

- [Oracle JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or [OpenJDK](http://openjdk.java.net/install/) version 6 or later
- [Apache Ant](http://ant.apache.org)

## Third Party Libraries

The Stanford CoreNLP XML Server uses the following third party libraries:

- [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml), a suite of core NLP tools
- [Simple](http://www.simpleframework.org), a Java based HTTP engine

The libraries can be downloaded and set up using the ant target `libs` (see [Installation](#installation)).

## License

- [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml) is licensed under the [GNU General Public License (v2 or later)](http://www.gnu.org/licenses/gpl-2.0.html).
- [Simple](http://www.simpleframework.org) is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

![GNU GPL v3](http://www.gnu.org/graphics/gplv3-127x51.png "GNU GPL v3")

Due to compatibility issues (see [GNU.org](http://www.gnu.org/licenses/license-list.html) and [Apache.org](http://www.apache.org/licenses/GPL-compatibility.html)), the Stanford CoreNLP XML Server is licensed under the [**GNU General Public License Version 3**](http://www.gnu.org/licenses/gpl-3.0.html).

# DSAlbums
An implementation of the Java client for the Data API, using a dataset of music albums. This is a companion repository for the "[Using the Data API on HCD](https://www.youtube.com/watch?v=LbcsVfm4A-k)" video on the [DataStax Developers](https://www.youtube.com/@DataStaxDevs) YouTube channel.

## Known issues
 - Menu resizes horizontally based on the contents.

## Requirements
<img src="dsalbums.png" width="300" align=right />

 - Java 21 (JRE)
 - Environment variables
     - `DB_API_ENDPOINT`
     - `DB_APPLICATION_TOKEN`
 - A database which supports the DataStax Data API:
    - Astra DB
    - DSE 6.9
    - HCD 1.0

## Database Schema
DSAlbums expects a collection named "albums" to exist in the "default_keyspace" namespace. The `albums` collection is expected to contain four properties:
 - `_id`: An autogenerated (type-4) UUID to act as a unique identifier for each album.
 - `title`: The album's title.
 - `band`: The musical group who composed the album.
 - `year`: The year that the album was released.

## Data

```
// Pink Floyd
{ "insertMany": {
     "documents":
        [{
            "title":"Pulse",
            "band":"Pink Floyd",
            "year":"1995"
        },
        {
            "title":"The Division Bell",
            "band":"Pink Floyd",
            "year":"1978"
        },
        {
            "title": "Dark Side of the Moon",
            "band": "Pink Floyd",
            "year": "1973"
        },
        {
            "title":"Meddle",
            "band":"Pink Floyd",
            "year":"1971"
        },
        {
            "title":"The Final Cut",
            "band":"Pink Floyd",
            "year":"1983"
        },
        {
            "title":"Atom Heart Mother",
            "band":"Pink Floyd",
            "year":"1970"
        },
        {
            "title": "A Momentary Lapse of Reason",
            "band": "Pink Floyd",
            "year": "1987"
        },
        {
            "title": "Wish You Were Here",
            "band": "Pink Floyd",
            "year": "1975"
        },
        {
            "title": "The Wall",
            "band": "Pink Floyd",
            "year": "1979"
        },
        {
            "title":"Animals",
            "band":"Pink Floyd",
            "year":"1977"
        }]
    }
}

// Dire Straits
{ "insertMany": {
     "documents":
        [{
            "title":"On the Road to Philadelphia",
            "band":"Dire Straits",
            "year":"1979"
        },
        {
            "title":"Dire Straits",
            "band":"Dire Straits",
            "year":"1978"
        },
        {
            "title":"On Every Street",
            "band":"Dire Straits",
            "year":"1991"
        },
        {
            "title":"Making Movies",
            "band":"Dire Straits",
            "year":"1980"
        },
        {
            "title":"On the Night",
            "band":"Dire Straits",
            "year":"1993"
        },
        {
            "title":"Dire Straits",
            "band":"Dire Straits",
            "year":"1978"
        }]
    }
}

// Rush
{ "insertMany": {
     "documents":
        [{
            "title":"Rush",
            "band":"Rush",
            "year":"1974"
        },
        {
            "title":"Fly By Night",
            "band":"Rush",
            "year":"1975"
        },
        {
            "title":"Hemispheres",
            "band":"Rush",
            "year":"1978"
        },
        {
            "title":"A Farewell to Kings",
            "band":"Rush",
            "year":"1977"
        },
        {
            "title": "Permanent Waves",
            "band": "Rush",
            "year": "1980"
        },
        {
            "title": "Moving Pictures",
            "band": "Rush",
            "year": "1981"
        },
        {
            "title": "Signals",
            "band": "Rush",
            "year": "1982"
        },
        {
            "title":"Grace Under Pressure",
            "band":"Rush",
            "year":"1984"
        },
        {
            "title":"Power Windows",
            "band":"Rush",
            "year":"1985"
        },
        {
            "title":"Hold Your Fire",
            "band":"Rush",
            "year":"1987"
        },
        {
            "title":"Presto",
            "band":"Rush",
            "year":"1989"
        },
        {
            "title": "Counterparts",
            "band": "Rush",
            "year": "1993"
        },
        {
            "title":"Test For Echo",
            "band":"Rush",
            "year":"1996"
        },
        {
            "title": "Clockwork Angels",
            "band": "Rush",
            "year": "2012"
        },
        {
            "title": "Snakes and Arrows",
            "band": "Rush",
            "year": "2007"
        },
        {
            "title": "Roll the Bones",
            "band": "Rush",
            "year": "1991"
        },
        {
            "title": "2112",
            "band": "Rush",
            "year": "1976"
        },        
        {
            "title":"Vapor Trails",
            "band":"Rush",
            "year":"2002"
        }]
    }
}
```

## To build:

### Build Requirements

 - Maven

The [pom.xml](pom.xml) file can be adjusted to build with earlier versions of Java, but it is not recommended to go below 17.

### Build command

    mvn clean install

### Running the build

    java -jar target/dsalbums-0.0.1-SNAPSHOT.jar

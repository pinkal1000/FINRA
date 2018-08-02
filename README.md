# FINRA

ABOUT :

This Project is about massaging the bi-weekly published short interest data on the FINRA website.

The source code, reads the "|" delimited FINRA data and converts it to JSON.

MILESTONES :

1. Today the load of all the FINRA data from the site takes under 500 seconds with a multi threaded loader spawning near a 100 simultaneous threads. 
2. It is a little over 700000 records read over http.

PROJECT PHASE :

1. At this point in time we have the hooks for all the backend data. 
2. Need to identify a NO SQL backend db to persist it now. 
3. Then I will be progressing on the next phase of the project which is the UI and the usefulness of this data.

PRE_REQUISITES :

1. Any Java runtime, preferably Java 8 or above.
2. Jackson open source Java Parser needs to be in the classpath for this source to compile.
3. Need JUnit libraries too to test the client and see it working in your preferable IDE like eclipse or on the command line.


FURTHER PLANS :

1. The intent is to process this data with open source frameworks like elastic search.
2. Create a Rich UI with this data.
3. Collect this data and eventually persist it to a NOSQL DB, thats the reason to convert to JSON.
4. Analyze all this data on a per stock basis to forecast future stock price movements using a machine learning predictive model.
5. A work in progress, Any feedback to improve would be greatly appreciated.

AUTHOR / DEVELOPER :

Pinkal Kadakia, A developer and enthusiast with new frameworks and development methodologies.

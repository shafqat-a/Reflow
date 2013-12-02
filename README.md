Reflow
======
A DTS Style ETL Tool written in C#. 

Project Goals:
--------------
1. Create a provider based ETL where there is a reader and a writer
2. Extensible so that more providers can be added
3. Serialize a ETL Job and run later
4. Scripting language support for transformation. VBScript, Javascript

Last Milestone : 0.2 
-----------------------
1. SQL and OLE DB Provider, Sql Server Writer implemented for bulk copy, OLE DB Reader (Tested with Access MDB)
2. Added some logging
3. Added ability discover table schema and also create that schema

Current Milestone : 0.3
--------------------
1. Clean up provider architecture
2. Add more logging
3. Add VBScript transformations


Next Milestone : 0.4
--------------------
3. Add serialization
4. Test with some more data tables

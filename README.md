# int2dt
 Some OMOP Concept related and Vocabulary CSV files contain date information. However, the csv files that can be downloaded from Athena have the date information formatted as YYYYMMDD. In Google Big Query, the Big Query import tool for CSV files expects the date to be formatted as YYYY-MM-DD. If bq tool is used to import this data with --autodetect will change the schema definition at the Big Query dataset.
 
 This tool can convert the date in CSV files so that Big Query can import the data without changing the schema.
 
 The usage:
 int2dt -i &lt;input file&gt; -o &lt;output file&gt;<br/>
&lt;input file&gt; must be one of CONCEPT.csv, CONCEPT_RELATIONSHIP.csv, or DRUG_STRENGTH.csv

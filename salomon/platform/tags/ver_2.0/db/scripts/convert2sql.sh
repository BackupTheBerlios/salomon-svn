#!/bin/bash

###############################################################################
#	Script converts coma separated values list to the SQL INSERT queries.
#
#	It requires 2 files:
#
#	1) table_definition_file - the file containing table definition 
#	in the following format:
#
#		tableName
#		field1
#		field2
#		...
#	e.g.
#		persons
#		person_id
#		name
#		surname
#
#	2) data_file - the file containing list of values in coma separated lines
#	
#	USAGE:
#
#		convert2sql <table_definition_file> <data_file>#
#	e.g.
#		convert2sql persons.def persons.txt
#
#	Generated SQL INSERT statements are printed on the stdout.
#
###############################################################################

# checking argument list
if [ $# -ne 2 ]
then
 echo Usage: $0 table_definition_file data_file;
 exit 1;
fi
 
# reading input parameters
fileDef=$1;
fileName=$2;
 
# counting fields in table
i=0;
for field in `cat $fileDef`
do
 fields[$i]=$field;
 let "i += 1";
done

# deleting old records
echo DELETE FROM ${fields[0]}\;;

# building query
# first line is a table name   
query=`echo INSERT INTO ${fields[0]} \(${fields[1]}`;
 
for ((i=2; i < ${#fields[@]}; i++))
do  
 query=`echo $query, ${fields[$i]}`;
done
 
query=`echo $query\) VALUES`;
 
# trims all white spaces in the input file
for line in `cat $fileName | sed -e 's/[ ^I][ ^I]*//g'`
do
 values=`echo $line | sed -e "s/,/\',\'/g"`		
 echo $query \(\'$values\'\)\;;
done


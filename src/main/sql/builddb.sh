#!/bin/bash
db=maptest
pDir=/Users/alex/IdeaProjects/CrashData

for filename in $pDir/src/main/sql/*.sql; do
    endName="${filename##*/}"
    shortName="${endName%.*}"
    psql -d $db -f $filename
    echo "copy $shortName from '$pDir/resources/ACCIDENT/$shortName.csv' delimiter ',' header csv;" | psql -d $db
done


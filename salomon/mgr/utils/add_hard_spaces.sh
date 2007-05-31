#!/usr/bin/sh
 
f__usage()
{
    echo "Usage: $0 <dir>"
}
 
WORD_LIST="i w a z o to na do za I W A Z O To Na Do Za"
 
##############################  MAIN SCRIPT  ##################################
 
if [ $# -ne 1 ]
then
    f__usage
    exit 1
fi
 
dir=$1
 
# Process all 'tex' files in given directory
for file in `find $dir -name *.tex`
do
    echo "Processing $file..."
    # Add hard space to all short words
    for word in $WORD_LIST
    do
        sed -i "s/ $word / $word~/g" $file
    done
done


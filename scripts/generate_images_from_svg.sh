#!/bin/bash

SOURCE_DIR=$(pwd)
DIMENSIONS="16 32 64 128 256 512"

while getopts "h?s:d:" opt; do
    case "$opt" in
    h|\?)
        show_help
        exit 0
        ;;
    d)  DIMENSIONS=$OPTARG
        ;;
    s)  SOURCE_DIR=$OPTARG
        ;;
    esac
done

EXPORT_DIR="${SOURCE_DIR}"

echo "svg source dir '${SOURCE_DIR}'"
echo "png export dir '${EXPORT_DIR}'"
echo "image dimensions to generate: $DIMENSIONS";

mkdir -p ${EXPORT_DIR}

for SVG_FILE in $(ls $SOURCE_DIR/*.svg); do

	echo -n "generating images for $SVG_FILE..."
	

	for IMAGE_SIZE in $DIMENSIONS; do
		
		if [[ $DIMENSIONS =~ \ |\' ]]
			FILENAME="$(basename -s svg ${SVG_FILE})_${IMAGE_SIZE}x${IMAGE_SIZE}png"
		then
			FILENAME="$(basename -s svg ${SVG_FILE})png"
		fi
		echo -n "${FILENAME}..."
		inkscape --without-gui --export-png "${EXPORT_DIR}/$FILENAME" "$SVG_FILE" -w ${IMAGE_SIZE} -h ${IMAGE_SIZE} > /dev/null
	done

	echo "done"

done

  

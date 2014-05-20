
wget http://download.geofabrik.de/north-america/us/new-york-latest.osm.bz2
bunzip2 new-york-latest.osm.bz2
mv new-york-latest.osm ny.osm.xml
osmosis --read-xml ny.osm.xml --bounding-box top=40.8011 right=-73.7608 left=-74.0595 bottom=40.5675 --write-xml nyc.osm.xml

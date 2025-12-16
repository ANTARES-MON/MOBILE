#!/bin/bash
echo "Setting up project configuration..."

# Create local.properties in each project
for project in MapsApp GPSLocation WIFI BLUETOOTH SMS CONVERSION PERIMETRE CALC LISTVIEW MENU SqlLiteF AllApps; do
    if [ -d "$project" ]; then
        echo "Setting up $project..."
        cp local.properties.template "$project/local.properties"
        echo "# $project specific configuration" >> "$project/local.properties"
        echo "" >> "$project/local.properties"
    fi
done

echo "Done! Edit each local.properties file with your API keys."
echo "Remember: NEVER commit local.properties to Git!"

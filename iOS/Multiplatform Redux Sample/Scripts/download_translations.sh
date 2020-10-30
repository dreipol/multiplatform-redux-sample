#!/bin/sh
READONLY_KEY="CdfsNn-KDkjPc2RK9hfGr7NvxFIPuaug"

DIR="$(cd "$(dirname "$0")"; pwd)";

function downloadLanguage {
    echo "fetching locale $1"
    TARGET_FILE="$SRCROOT/$PROJECT_NAME/Resources/$2.lproj/Localizable.strings"
    rm $TARGET_FILE 2> /dev/null
    curl -f -L -s "https://localise.biz:443/api/export/locale/$1.strings?key=$READONLY_KEY" | sed -e '6,7d' > "${TARGET_FILE}"
}

languages=( "de" "en" )
for i in "${languages[@]}"
do
    downloadLanguage $i $i
done

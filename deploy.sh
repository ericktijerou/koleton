#!/bin/bash
for i in $(ls -d */); do
  m=${i%%/}
  if [[ $m == koleton* ]]; then
    echo ">> Deploying $m ..."
    ./gradlew :$m:clean :$m:build :$m:bintrayUpload -PbintrayUser=$BINTRAY_USER -PbintrayKey=$BINTRAY_KEY -PdryRun=false -Ppublish=true --no-configure-on-demand --no-parallel
  fi
done

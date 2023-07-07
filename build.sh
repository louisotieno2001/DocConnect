#!/bin/sh

props="./build.properties"

# compile android apk
ant bundle

while IFS='=' read -r key value 
do
   key=$(echo $key | tr '.' '_')
   
   if ! test -z $key ;
   then
     prop=$(echo ${key}=${value})
     eval $prop
   fi

done < "$props"

CWD=$(pwd)
BUILD=$CWD/$build_path

echo "Re bundling apk"

cd $BUILD

mkdir -p rebundle
# unzipping res.apk
unzip -uq res.apk -d rebundle/

cd $BUILD/rebundle

echo "Uncompressing resources.arsc"

zip -q -n "resources.arsc" -r  $BUILD/repack.apk * 

# zip aligning unsigned.apk 
cd $BUILD
zipalign -f 4 repack.apk unsigned.apk 

cd $CWD
echo "Signing"
apksigner sign --ks $keystore_debug --ks-key-alias $keystore_debug_alias --ks-pass pass:$keystore_debug_pass --out $BUILD/signed-debug.apk $BUILD/unsigned.apk

waydroid app install  build/signed-debug.apk


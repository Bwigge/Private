This PhoneGap folder has many symlinks to reduce redundant code,

When building packages, remove the symlinks by copying the folders by using the following
bash command:

1. cp -RL "folder" "new_folder"

OR

cp -r "folder" "new_folder"

2. Remove the rsync command in the iPhone project

Steps
a) Select the Project name -> Select your Target -> Build Phases
b) In the RunScript tab, remove rsync(all) the scripts.


GENERATING DOCS

1. Make sure the following is installed: Ruby, RubyGem and Sencha's JSDuck
2. Run JSDuck with the following command from the PhoneGap/docs folder:

jsduck attAPI.js \
--warnings=-no_doc,-dup_member,-link_ambiguous \
--external=XMLHttpRequest \
--output "ATT-PhoneGap-Documentation" \
--title "ATT PhoneGap API Documentation" \

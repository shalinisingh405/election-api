# election-api

Follow these

```declarative
…or create a new repository on the command line
echo "# election-api" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin git@github.com:shalinisingh405/election-api.git
git push -u origin main
```
if any error while pushing to git for ex>

''' error: failed to push some refs to 'github.com:shalinisingh405/election-api.git''

do these -
```declarative
git reset --mixed origin/main
git add .
git commit -m "This is a new commit for what I originally planned to be amended"
git push origin main
```

* If username is difference while checkin to git repo use below to fix username : 

```declarative
amitkumar@MacBook-Pro election-api-java % git config user.email "shalini.singh405@gmail.com"
amitkumar@MacBook-Pro election-api-java % git commit -m "git issue fix"
[main 7cc1b75] git issue fix
 1 file changed, 0 insertions(+), 0 deletions(-)
 create mode 100644 git-issue-fixed.md

amitkumar@MacBook-Pro election-api-java % git push origin main
Enumerating objects: 4, done.
Counting objects: 100% (4/4), done.
Delta compression using up to 8 threads
Compressing objects: 100% (2/2), done.
Writing objects: 100% (3/3), 287 bytes | 287.00 KiB/s, done.
Total 3 (delta 1), reused 0 (delta 0), pack-reused 0
remote: Resolving deltas: 100% (1/1), completed with 1 local object.
To github.com:shalinisingh405/election-api.git
   913cd24..7cc1b75  main -> main
amitkumar@MacBook-Pro election-api-java % 

```
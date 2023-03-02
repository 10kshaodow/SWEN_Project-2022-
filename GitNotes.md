# Git Branching

## Create a new feature branch

For this example, this will be our current branching situation

```
Master--------------------------->
      \
       Sprint2------------------->
```

1. `git checkout Sprint2` Move to the branch you would like to create a branch
2. `git pull` make sure you are up to date
3. `git branch Feature1` add a new branch called Feature1

Your tree will now look like this

```
Master------------------------>
      \
       Sprint2---------------->
              \
               Feature1------->
```

1. `git checkout Feature1` Switch to edit your branch
2. `git push --set-upstream origin Testing-User` will push this branch to GitHub, and will automatically pull code from this branch in GitHub during a `git pull` command.
3. Edit the branch to create your feature and make frequent commits

## Merge a feature branch

Once you have finished developing your feature, you need to merge your changes so that the Sprint2 branch will work with your feature.

1. `git checkout Sprint2` Switch back to the branch you want to merge to
2. `git pull` ensure that Sprint2 is up to date
3. `git checkout Feature1` Switch back to your feature
4. `git merge Sprint2` Merge Sprint2 into Feature1
5. resolve all merge conflicts and make sure your code fully works with Sprint2
   Feature1 will now contain Sprint2 and your feature, Sprint2 should be unchanged.<br/>

```
Master------------------------>
      \
       Sprint2---------------->
              \           \ (merge Sprint2 to Feature1)
               Feature1------->
```

6. `git add .` add the merge changes
7. `git commit -m "Merged Sprint 2 into Feature1"` commit your changes
8. `git push` push your changes to github

You will then create a pull request on GitHub for someone to review and merge your changes into Sprint2

## Create a GitHub pull request

Creating a pull request on GitHub will allow someone to review your code and merge your feature with another branch.

1. Navigate to our repository at https://github.com/RIT-SWEN-261-05/team-project-2221-swen-261-05-d-bigdevelopment
2. In the navigation at the top, you will see `Code` | `Issues` | `Pull requests` | ... Click the tab | `Pull requests` | (NOT at the very top of the page)
3. Select "New pull request"
4. Select your branch to merge (Feature1) into the branch you want to merge into (Sprint2). Then click "Create pull request" and add notes for someone to review

## Accept a GitHub pull request

Accepting a pull request on GitHub will allow you to review someones code and merge their features into the current branch.

1. Navigate to our repository at https://github.com/RIT-SWEN-261-05/team-project-2221-swen-261-05-d-bigdevelopment
2. In the navigation at the top, you will see `Code` | `Issues` | `Pull requests` | ... Click the tab | `Pull requests` | (NOT at the very top of the page)
3. Select the pull request that you want to review
4. Review and accept the changes if everything is working. Select "Merge pull request"

The Feature (Feature1) will now be merged into the original branch (Sprint2)

```
Master------------------------->
      \
       Sprint2----------------->
              \           \   / (Pull request Merges Feature1 back to Sprint2)
               Feature1-------->
```

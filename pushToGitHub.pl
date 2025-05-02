#!/usr/bin/perl -I/home/phil/perl/cpan/DataTableText/lib/
#-------------------------------------------------------------------------------
# Push Btree block code to GitHub .
# Philip R Brenan at gmail dot com, Appa Apps Ltd Inc., 2024
#-------------------------------------------------------------------------------
use v5.38;
use warnings FATAL => qw(all);
use strict;
use Carp;
use Data::Dump qw(dump);
use Data::Table::Text qw(:all);
use GitHub::Crud qw(:all);

my $home    = q(/home/phil/z/java/binarySearch/);                                # Home folder
my $md5File = fpf $home, qw(.md5Sums);                                          # Md5 file sums for each known file to detect changes
my $user    = q(philiprbrenan);                                                 # User
my $repo    = q(binarySearchAndMove);                                           # Repo
my $wf      = q(.github/workflows/main.yml);                                    # Work flow on Ubuntu
my @ext     = qw(.java .pl .md);                                                # Extensions of files to upload to github
my @jx      = qw(.java);                                                        # Extensions of java files

say STDERR timeStamp,  " push to github $repo";

my @files = searchDirectoryTreesForMatchingFiles($home, @ext);                  # Files of interest
   @files = changedFiles $md5File, @files;                                      # Filter out files that have not changed

if (!@files)                                                                    # No new files
 {say "Everything up to date";
  exit;
 }

if  (1)                                                                         # Upload via github crud
 {for my $s(@files)                                                             # Upload each selected file
   {my $c = readBinaryFile $s;                                                  # Load file

    $c = expandWellKnownWordsAsUrlsInMdFormat $c if $s =~ m(README);            # Expand README

    my $t = swapFilePrefix $s, $home;                                           # File on github
    my $w = writeFileUsingSavedToken($user, $repo, $t, $c);                     # Write file into github
    lll "$w  $t";
   }
 }

if (1)                                                                          # Write workflow
 {my @j = searchDirectoryTreesForMatchingFiles($home, @jx);                     # Java files
     @j = map {fn $_} @j;                                                       # Java class names

  my $d = dateTimeStamp;
  my $c = q(com/AppaApps/Silicon);                                              # Package to classes folder
  my $j = join ', ', @j;                                                        # Java files
  my $y = <<"END";
# Test $d

name: Test
run-name: $repo

on:
  push:
    paths:
      - '**/main.yml'

concurrency:
  group: \${{ github.workflow }}-\${{ github.ref }}
  cancel-in-progress: true

jobs:

  test:
    permissions: write-all
    runs-on: ubuntu-latest

    strategy:
      matrix:
        task: [$j]

    steps:
    - uses: actions/checkout\@v4
      with:
        ref: 'main'

    - name: 'JDK 24'
      uses: oracle-actions/setup-java\@v1
      with:
        website: jdk.java.net

    - name: Java release
      run: |
        java -version

    - name: Position files in package
      run: |
        mkdir -p $c
        cp *.java $c

    - name: Java
      run: |
        javac -g -d Classes -cp Classes $c/*.java
END

  for my $j(@j)                                                                 # Java files
   {$y .= <<END;

    - name: Test $j
      if: matrix.task == '$j'
      run: |
        java -cp Classes $c/$j

END
   }

  my $f = writeFileUsingSavedToken $user, $repo, $wf, $y;                       # Upload workflow
  lll "$f  Ubuntu work flow for $repo";
 }
else
 {say STDERR "No Java files changed";
 }

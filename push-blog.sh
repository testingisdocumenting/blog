#!/usr/bin/env bash

set -eux

GIT_URL=$(git config --get remote.origin.url)
GH_PAGES_BRANCH=gh-pages

ROOT_DIR=$(dirname "$0")
GH_PAGES_DIR=$ROOT_DIR/gh-pages

# Cleanup on exit
cleanup() {
  rm -rf "$GH_PAGES_DIR"
}

trap cleanup EXIT

git clone "$GIT_URL" --branch $GH_PAGES_BRANCH --single-branch --depth 1 "$GH_PAGES_DIR"

mkdir -p "$GH_PAGES_DIR"/

## Cleanup existing pages
rm -rf "$GH_PAGES_DIR"//*

# Copy in new blog pages
cp -r "$ROOT_DIR"/blog-content/target/blog/* "$GH_PAGES_DIR"/

pushd "$GH_PAGES_DIR"

## Tell git about changed, new and deleted pages
git add -A

git commit -m "Updating blog"
git push

## change back to root dir to allow cleanup to work properly
popd

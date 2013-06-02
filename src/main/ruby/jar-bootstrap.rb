#!/usr/bin/env ruby

# Nasty kludge to get the jar:file uri.
# There must be a better way.
java_base = Gem::dir.split('!')[0]

dirs = Dir.glob "#{java_base}!/gems/*/lib"
dirs.each{|dir| $LOAD_PATH.unshift dir}

require "fpm"
require "fpm/command"

module Gem
  def self.dir
    `dirname $(gem which rubygems)`.chomp
  end
end

(FPM::Command.run || 0)

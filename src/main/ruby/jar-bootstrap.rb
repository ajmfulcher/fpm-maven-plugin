#!/usr/bin/env ruby

require 'java'
java_import 'uk.me.ajmfulcher.fpmplugin.JrubyHelper'

java_paths = JrubyHelper.new.get_jar_path

dirs = []
java_paths.each do |path|
  Dir.glob("#{path}/gems/*/lib").each{|d| dirs << d}
end

dirs.each{|dir| $LOAD_PATH.unshift dir}

require "fpm"
require "fpm/command"

module Gem
  def self.dir
    `dirname $(gem which rubygems)`.chomp
  end
end

(FPM::Command.run || 0)

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: firstTest.proto

package com.example.grpcprotocol;

public interface SendBooksInfoRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpcprotocol.SendBooksInfoRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 id = 1;</code>
   * @return The id.
   */
  int getId();

  /**
   * <code>map&lt;string, string&gt; data = 2;</code>
   */
  int getDataCount();
  /**
   * <code>map&lt;string, string&gt; data = 2;</code>
   */
  boolean containsData(
      java.lang.String key);
  /**
   * Use {@link #getDataMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getData();
  /**
   * <code>map&lt;string, string&gt; data = 2;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getDataMap();
  /**
   * <code>map&lt;string, string&gt; data = 2;</code>
   */

  java.lang.String getDataOrDefault(
      java.lang.String key,
      java.lang.String defaultValue);
  /**
   * <code>map&lt;string, string&gt; data = 2;</code>
   */

  java.lang.String getDataOrThrow(
      java.lang.String key);
}
package io.phanisment.itemcaster.util;

public final class StringUtil {
  private static final int MAX_VALID_CHAR = 128;
  private static final boolean[] VALID_CHAR = new boolean[MAX_VALID_CHAR];

  public static String getFileName(String file_name) {
    int pos = file_name.lastIndexOf('.');
    return file_name.substring(pos);
  }

  public static String getFileFormat(String file_name) {
    int pos = file_name.lastIndexOf('.');
    return file_name.substring(pos, file_name.length());
  }

  /**
   * NOTE: This is used for optimization, instead using regex that can be heavy.
   * 
   * @param text 
   * @return
   */
  public static boolean containsSpecial(String string) {
    if (string == null || string.isEmpty()) return false;
    for (int i = 0; i < string.length(); i++) if (!allowedChar(string.charAt(i))) return true;
    return false;
  }

  private static boolean allowedChar(char c) {
    return c < MAX_VALID_CHAR && VALID_CHAR[c];
  }

  static {
    for (char c = 'A'; c <= 'Z'; c++) VALID_CHAR[c] = true;
    for (char c = 'a'; c <= 'z'; c++) VALID_CHAR[c] = true;
    for (char c = '0'; c <= '9'; c++) VALID_CHAR[c] = true;
    VALID_CHAR['_'] = true;
    VALID_CHAR['.'] = true;
  }
}
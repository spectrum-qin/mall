package com.spectrum.mall.utils.bean.test;

import java.io.*;

public abstract class Serializations {
    public Serializations() {
    }

    public static Serializable copy(Serializable obj) {
        return obj == null ? obj : deserialize(serialize(obj));
    }

    public static byte[] serialize(Serializable s) {
        if (s == null) {
            return null;
        } else {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                Throwable var2 = null;

                Object var5;
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    Throwable var4 = null;

                    try {
                        oos.writeObject(s);
                        oos.flush();
                        var5 = bos.toByteArray();
                    } catch (Throwable var30) {
                        var5 = var30;
                        var4 = var30;
                        throw var30;
                    } finally {
                        if (oos != null) {
                            if (var4 != null) {
                                try {
                                    oos.close();
                                } catch (Throwable var29) {
                                    var4.addSuppressed(var29);
                                }
                            } else {
                                oos.close();
                            }
                        }

                    }
                } catch (Throwable var32) {
                    var2 = var32;
                    throw var32;
                } finally {
                    if (bos != null) {
                        if (var2 != null) {
                            try {
                                bos.close();
                            } catch (Throwable var28) {
                                var2.addSuppressed(var28);
                            }
                        } else {
                            bos.close();
                        }
                    }

                }

                return (byte[])var5;
            } catch (Exception var34) {
                throw new RuntimeException("failed to serialize s: " + s, var34);
            }
        }
    }

    public static Serializable deserialize(byte[] data) {
        return data != null && data.length != 0 ? deserialize(data, 0, data.length) : null;
    }

    public static Serializable deserialize(byte[] data, int offset, int length) {
        if (data != null && data.length != 0) {
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(data, offset, length);
                Throwable var4 = null;

                Object var7;
                try {
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    Throwable var6 = null;

                    try {
                        var7 = (Serializable)ois.readObject();
                    } catch (Throwable var32) {
                        var7 = var32;
                        var6 = var32;
                        throw var32;
                    } finally {
                        if (ois != null) {
                            if (var6 != null) {
                                try {
                                    ois.close();
                                } catch (Throwable var31) {
                                    var6.addSuppressed(var31);
                                }
                            } else {
                                ois.close();
                            }
                        }

                    }
                } catch (Throwable var34) {
                    var4 = var34;
                    throw var34;
                } finally {
                    if (bis != null) {
                        if (var4 != null) {
                            try {
                                bis.close();
                            } catch (Throwable var30) {
                                var4.addSuppressed(var30);
                            }
                        } else {
                            bis.close();
                        }
                    }

                }

                return (Serializable)var7;
            } catch (Exception var36) {
                throw new RuntimeException("failed to deserialize", var36);
            }
        } else {
            return null;
        }
    }
}

package com.spectrum.mall.utils.text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ResourceLoaderUtils {
    private static final Logger log = LoggerFactory.getLogger(ResourceLoaderUtils.class);

    public ResourceLoaderUtils() {
    }

    public static void load(ResourceLoader resourceLoader, String resourcePaths) {
        resourcePaths = resourcePaths.trim();
        if (!"".equals(resourcePaths)) {
            List<String> result = new ArrayList();
            String[] var3 = resourcePaths.split("[,，]");
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String resource = var3[var5];

                try {
                    resource = resource.trim();
                    if (resource.startsWith("classpath:")) {
                        result.addAll(loadClasspathResource(resource.replace("classpath:", ""), resourceLoader, resourcePaths));
                    } else if (resource.startsWith("http:")) {
                        result.addAll(loadHttpResource(resource, resourceLoader));
                    } else {
                        result.addAll(loadNoneClasspathResource(resource, resourceLoader, resourcePaths));
                    }
                } catch (Exception var8) {
                    log.error("加载资源失败：" + resource, var8);
                }
            }

            resourceLoader.clear();
            resourceLoader.load(result);
        }
    }

    private static List<String> loadNoneClasspathResource(String resource, ResourceLoader resourceLoader, String resourcePaths) throws IOException {
        List<String> result = new ArrayList();
        Path path = Paths.get(resource);
        boolean exist = Files.exists(path, new LinkOption[0]);
        if (!exist) {
            return result;
        } else {
            boolean isDir = Files.isDirectory(path, new LinkOption[0]);
            if (isDir) {
                result.addAll(loadAndWatchDir(path, resourceLoader, resourcePaths));
            } else {
                result.addAll(load(resource));
            }

            return result;
        }
    }

    private static List<String> loadHttpResource(String resource, ResourceLoader resourceLoader) throws MalformedURLException, IOException {
        List<String> result = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader((new URL(resource)).openConnection().getInputStream(), "utf-8"));
        Throwable var4 = null;

        try {
            String line = null;

            while((line = reader.readLine()) != null) {
                line = line.trim();
                if (!"".equals(line) && !line.startsWith("#")) {
                    result.add(line);
                }
            }
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            if (reader != null) {
                if (var4 != null) {
                    try {
                        reader.close();
                    } catch (Throwable var12) {
                        var4.addSuppressed(var12);
                    }
                } else {
                    reader.close();
                }
            }

        }

        return result;
    }

    private static List<String> loadClasspathResource(String resource, ResourceLoader resourceLoader, String resourcePaths) throws IOException {
        List<String> result = new ArrayList();
        Enumeration ps = ResourceLoaderUtils.class.getClassLoader().getResources(resource);

        while(ps.hasMoreElements()) {
            URL url = (URL)ps.nextElement();
            if (url.getFile().contains(".jar!")) {
                result.addAll(load("classpath:" + resource));
            } else {
                File file = new File(url.getFile());
                boolean dir = file.isDirectory();
                if (dir) {
                    result.addAll(loadAndWatchDir(file.toPath(), resourceLoader, resourcePaths));
                } else {
                    result.addAll(load(file.getAbsolutePath()));
                }
            }
        }

        return result;
    }

    private static List<String> loadAndWatchDir(Path path, ResourceLoader resourceLoader, String resourcePaths) {
        final ArrayList result = new ArrayList();

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    result.addAll(ResourceLoaderUtils.load(file.toAbsolutePath().toString()));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException var5) {
            log.error("加载资源失败：" + path, var5);
        }

        return result;
    }

    private static List<String> load(String path) {
        ArrayList result = new ArrayList();

        try {
            InputStream in = null;
            if (path.startsWith("classpath:")) {
                in = ResourceLoaderUtils.class.getClassLoader().getResourceAsStream(path.replace("classpath:", ""));
            } else {
                in = new FileInputStream(path);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)in, "utf-8"));
            Throwable var4 = null;

            try {
                String line;
                try {
                    while((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (!"".equals(line) && !line.startsWith("#")) {
                            result.add(line);
                        }
                    }
                } catch (Throwable var14) {
                    var4 = var14;
                    throw var14;
                }
            } finally {
                if (reader != null) {
                    if (var4 != null) {
                        try {
                            reader.close();
                        } catch (Throwable var13) {
                            var4.addSuppressed(var13);
                        }
                    } else {
                        reader.close();
                    }
                }

            }
        } catch (Exception var16) {
            log.error("加载资源失败：" + path, var16);
        }

        return result;
    }
}

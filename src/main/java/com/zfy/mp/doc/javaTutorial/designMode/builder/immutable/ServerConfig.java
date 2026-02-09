package com.zfy.mp.doc.javaTutorial.designMode.builder.immutable;

/**
 * 不可变类配置 - 不可变建造者模式
 */
final class ServerConfig {
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final int timeout;
    private final boolean sslEnabled;

    private ServerConfig(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
        this.timeout = builder.timeout;
        this.sslEnabled = builder.sslEnabled;
    }

    public String getHost() { return host; }
    public int getPort() { return port; }
    public String getUsername() { return username; }
    public int getTimeout() { return timeout; }
    public boolean isSslEnabled() { return sslEnabled; }

    @Override
    public String toString() {
        return String.format("ServerConfig{host='%s', port=%d, user='%s', timeout=%d, ssl=%s}",
            host, port, username, timeout, sslEnabled);
    }

    public static class Builder {
        // 必填参数
        private String host;

        // 可选参数（使用默认值）
        private int port = 8080;
        private String username = "guest";
        private String password = "";
        private int timeout = 30000;
        private boolean sslEnabled = false;

        public Builder(String host) {
            this.host = host;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder sslEnabled(boolean sslEnabled) {
            this.sslEnabled = sslEnabled;
            return this;
        }

        public ServerConfig build() {
            if (host == null || host.isEmpty()) {
                throw new IllegalStateException("Host cannot be null or empty");
            }
            if (port < 0 || port > 65535) {
                throw new IllegalStateException("Port must be between 0 and 65535");
            }
            return new ServerConfig(this);
        }
    }
}

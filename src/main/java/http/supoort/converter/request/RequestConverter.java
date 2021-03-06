package http.supoort.converter.request;

import http.exceptions.IllegalHttpRequestException;
import http.model.request.ServletRequest;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestConverter {
    private static final String SEPARATOR = " ";

    public ServletRequest parse(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String requestLine = IOUtils.readFirstLine(bufferedReader);
        try {
            validate(requestLine);
            String[] requestLineTokens = requestLine.split(SEPARATOR);
            String method = requestLineTokens[0];
            return HttpRequestConverters.of(method).convert(requestLineTokens[1], requestLineTokens[2], bufferedReader);

        } catch (IndexOutOfBoundsException e) {
            throw new IllegalHttpRequestException(e.getMessage());
        }
    }

    private void validate(String requestLine) {
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IllegalHttpRequestException();
        }
    }
}

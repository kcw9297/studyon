package studyon.app.common.infra.aws;

import jakarta.servlet.http.HttpServletResponse;

public interface AWSCloudFrontProvider {

    void setSignedCookies(HttpServletResponse response);


}

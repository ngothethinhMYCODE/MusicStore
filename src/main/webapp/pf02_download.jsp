<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>
        Murach's Java Servlets and JSP - Downloads
    </title>
    <link
        rel="stylesheet"
        href="styles/main.css"
        type="text/css">
	
</head>
<body>
    <h1>Downloads</h1>

    <h2>
        86 (the band) - True Life Songs and Pictures
    </h2>

    <table>
        <tr>
            <th>Song title</th>
            <th>Audio Format</th>
        </tr>
        <tr>
            <td>
                You Are a Star
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/MusicStore/sound/${productCode}/star.mp4">
                    MP4
                </a>
            </td>
        </tr>
        <tr>
            <td>
                Don't Make No Difference
            </td>

            <td>
                <a href="${pageContext.request.contextPath}/MusicStore/sound/${productCode}/no_difference.mp4">
                    MP4
                </a>
            </td>
        </tr>
    </table>
</body>
</html>


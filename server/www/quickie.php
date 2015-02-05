<?php
$jsonData = getData("http://quickat.cpollet.net/api/quickies/");
makePage($jsonData);

function getData($siteRoot) {
    $id = ctype_digit($_GET['id']) ? $_GET['id'] : 1;
    $rawData = file_get_contents($siteRoot.$id);
    return json_decode($rawData);
}

function makePage($data) {
    ?>
    <!DOCTYPE html>
    <html>
        <head>
            <meta property="og:title" content="<?php echo $data->quickie->title; ?>" />
            <meta property="og:description" content="<?php echo $data->quickie->description; ?>" />
            <meta property="og:image" content="http://quickat.cpollet.net/<?php echo $data->userGroup->icon ?>" />
            <!-- <meta property="og:image" content="" />-->
            <!-- etc. -->
            <title><?php echo $data->quickie->title ?></title>
        </head>
        <body>
            <img src="/<?php echo $data->userGroup->icon ?>">
            <p><?php echo $data->quickie->description; ?></p>
        </body>
    </html>
    <?php
}

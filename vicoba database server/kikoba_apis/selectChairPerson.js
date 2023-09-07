const { pool } = require("../database");

function selectChairPerson(req, res) {
  var { userID, kikobaKey, memberKey, kikobaName } = req.body;

  //Query to check if their is a chair person already for a particular kikoba.
  pool.query(
    'SELECT "kikobaKey" FROM "chair_person" WHERE "kikobaKey" = ' +
      kikobaKey +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }

      if (results.rows.length == 0) {
        //Query to add new chair person of a particular kikoba.
        pool.query(
          'INSERT INTO "chair_person" VALUES (' +
            kikobaKey +
            "," +
            memberKey +
            ");",
          (error, results) => {
            if (error) {
              throw error;
            }
          }
        );

        //Query to notify user after being selected as a chair person of a particular kikoba.
        pool.query(
          'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", "kikobaInvolved") VALUES (' +
            userID +
            ",'Mwenyekiti wa kikoba.','Umechaguliwa kuwa mwenyekiti wa kikoba \"" +
            kikobaName +
            "\".'," +
            kikobaKey +
            ");",
          (error, results) => {
            if (error) {
              throw error;
            }
          }
        );
      } else {
        //Query to update a chair person of a particular kikoba.
        pool.query(
          'UPDATE "chair_person" SET "memberKey"= ' +
            memberKey +
            ' WHERE "kikobaKey" =' +
            kikobaKey +
            ";",
          (error, results) => {
            if (error) {
              throw error;
            }
          }
        );

        //Query to notify user after being selected as a chair person of a particular kikoba.
        pool.query(
          'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", "kikobaInvolved") VALUES (' +
            userID +
            ",'Mwenyekiti wa kikoba.','Umechaguliwa kuwa mwenyekiti wa kikoba \"" +
            kikobaName +
            "\".'," +
            kikobaKey +
            ");",
          (error, results) => {
            if (error) {
              throw error;
            }
          }
        );
      }

      res.json(true);
    }
  );
}

module.exports = { selectChairPerson };

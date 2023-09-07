const { pool } = require("../database");

function selectSecretary(req, res) {
  var { userID, kikobaKey, memberKey, kikobaName } = req.body;

  //Query to check if their is a secretary already for a particular kikoba.
  pool.query(
    'SELECT "kikobaKey" FROM "secretary" WHERE "kikobaKey" = ' +
      kikobaKey +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }

      if (results.rows.length == 0) {
        //Query to add new secretary of a particular kikoba.
        pool.query(
          'INSERT INTO "secretary" VALUES (' +
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

        //Query to notify user after being selected as a secretary of a particular kikoba.
        pool.query(
          'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", "kikobaInvolved") VALUES (' +
            userID +
            ",'Katibu wa kikoba.','Umechaguliwa kuwa katibu wa kikoba \"" +
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
        //Query to update new secretary of a particular kikoba.
        pool.query(
          'UPDATE "secretary" SET "memberKey"= ' +
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

        //Query to notify user after being selected as a secretary of a particular kikoba.
        pool.query(
          'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", "kikobaInvolved") VALUES (' +
            userID +
            ",'Katibu wa kikoba.','Umechaguliwa kuwa katibu wa kikoba \"" +
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

module.exports = { selectSecretary };

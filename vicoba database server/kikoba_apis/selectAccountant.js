const { pool } = require("../database");

function selectAccountant(req, res) {
  var { userID, kikobaKey, memberKey, kikobaName } = req.body;

  //Query to check if their is an accountant already for a particular kikoba.
  pool.query(
    'SELECT "kikobaKey" FROM "accountant" WHERE "kikobaKey" = ' +
      kikobaKey +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }

      if (results.rows.length == 0) {
        //Query to add new accountant of a particular kikoba.
        pool.query(
          'INSERT INTO "accountant" VALUES (' +
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

        //Query to notify user after being selected as an accountant of a particular kikoba.
        pool.query(
          'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", "kikobaInvolved") VALUES (' +
            userID +
            ",'Mhasibu wa kikoba.','Umechaguliwa kuwa mhasibu wa kikoba \"" +
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
        //Query to update new accountant of a particular kikoba.
        pool.query(
          'UPDATE "accountant" SET "memberKey"= ' +
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

        //Query to notify user after being selected as an accountant of a particular kikoba.
        pool.query(
          'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", "kikobaInvolved") VALUES (' +
            userID +
            ",'Mhasibu wa kikoba.','Umechaguliwa kuwa mhasibu wa kikoba \"" +
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

module.exports = { selectAccountant };

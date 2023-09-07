const { pool } = require("../database");

function selectAdmin(req, res) {
  var { userID, kikobaKey, memberKey, kikobaName } = req.body;

  //Query to check if their is an admin already for a particular kikoba.
  pool.query(
    'SELECT "kikobaKey" FROM "admin" WHERE "kikobaKey" = ' + kikobaKey + ";",
    (error, results) => {
      if (error) {
        throw error;
      }

      if (results.rows.length == 0) {
        //Query to add new admin of a particular kikoba.
        pool.query(
          'INSERT INTO "admin" VALUES (' + kikobaKey + "," + memberKey + ");",
          (error, results) => {
            if (error) {
              throw error;
            }
          }
        );

        //Query to notify user after being selected as an admin of a particular kikoba.
        pool.query(
          'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", "kikobaInvolved") VALUES (' +
            userID +
            ",'Adimini wa kikoba.','Umechaguliwa kuwa adimini wa kikoba \"" +
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
        //Query to update new admin of a particular kikoba.
        pool.query(
          'UPDATE "admin" SET "memberKey"= ' +
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

        //Query to notify user after being selected as an admin of a particular kikoba.
        pool.query(
          'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", "kikobaInvolved") VALUES (' +
            userID +
            ",'Adimini wa kikoba.','Umechaguliwa kuwa adimini wa kikoba \"" +
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

module.exports = { selectAdmin };

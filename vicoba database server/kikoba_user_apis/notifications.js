const { pool } = require("../database");

function getUserNotifications(req, res) {
  var { userID } = req.body;

  //Query to retrieve user notifications
  pool.query(
    'SELECT "notificationID", notified, "notificationTitle", "notificationBody","notifiedDate", COALESCE("kikobaInvolved",0) AS "kikobaInvolved" FROM "notification" WHERE notified = ' +
      userID +
      ' ORDER BY "notifiedDate" DESC;',
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows);
    }
  );
}

module.exports = { getUserNotifications };

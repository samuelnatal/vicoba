const { pool } = require("../database");

function deleteViewedNotification(req, res) {
  var { notificationID } = req.body;

  //Query to delete viewed notification.
  pool.query(
    'DELETE FROM "notification" WHERE "notificationID" = ' +
      notificationID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }

      res.json(true);
    }
  );
}

module.exports = { deleteViewedNotification };

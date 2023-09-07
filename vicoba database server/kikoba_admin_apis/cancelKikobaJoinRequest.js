const { pool } = require("../database");

function cancelKikobaJoinRequest(req, res) {
  var { userID, kikobaID } = req.body;

  //Query to update the request of the user to joing kikoba as rejected
  pool.query(
    'UPDATE "member" SET status = \'Rejected\' WHERE "userKey" =' +
      userID +
      ' AND "kikobaKey" = ' +
      kikobaID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }

      //Query to retrieve the name of kikoba user has been rejected of.
      pool.query(
        'SELECT "kikobaName" FROM "kikoba" WHERE "kikobaID" = ' +
          kikobaID +
          ";",
        (error, results) => {
          if (error) {
            throw error;
          }

          //Query to save new notification after rejecting a user request to join kikoba group.
          pool.query(
            'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody","kikobaInvolved") VALUES (' +
              userID +
              ",'Kikoba request.','Sorry!, your request to join " +
              results.rows[0].kikobaName +
              " has been rejected.'," +
              kikobaID +
              ");",
            (error, results) => {
              if (error) {
                throw error;
              }
            }
          );
        }
      );

      //Send true to the vicoba app to notify it of successful operation
      res.json(true);
    }
  );
}

module.exports = { cancelKikobaJoinRequest };

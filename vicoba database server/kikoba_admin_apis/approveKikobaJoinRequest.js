const { pool } = require("../database");

function approveKikobaJoinRequest(req, res) {
  var { userID, kikobaID } = req.body;

  //Query to update user request status as approved
  pool.query(
    'UPDATE "member" SET status = \'Approved\' WHERE "userKey" =' +
      userID +
      ' AND "kikobaKey" = ' +
      kikobaID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }

      //Query to retrieve the name of kikoba in which user has been approved of.
      pool.query(
        'SELECT "kikobaName" FROM "kikoba" WHERE "kikobaID" = ' +
          kikobaID +
          ";",
        (error, results) => {
          if (error) {
            throw error;
          }

          //Query to save new notification after succefful approving a user as a new member.
          pool.query(
            'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", "kikobaInvolved") VALUES (' +
              userID +
              ",'Kikoba request.','Congratulations!, your request to join " +
              results.rows[0].kikobaName +
              " has been approved.',"+kikobaID+");",
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

module.exports = { approveKikobaJoinRequest };

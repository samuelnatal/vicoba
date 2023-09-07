const { pool } = require("../database");

function inviteUserToJoinKikoba(req, res) {
  var { userID, kikobaID } = req.body;

  //Query to add user to a member table with status as invited
  pool.query(
    'INSERT INTO "member" ("userKey", "kikobaKey", status) VALUES (' +
      userID +
      "," +
      kikobaID +
      ", ' Invited' );",
    (error, results) => {
      if (error) {
        throw error;
      }

      //Query to retrieve the name of kikoba user has been invited of.
      pool.query(
        'SELECT "kikobaName" FROM "kikoba" WHERE "kikobaID" = ' +
          kikobaID +
          ";",
        (error, results) => {
          if (error) {
            throw error;
          }

          //Query to save invitation notification for a user.
          pool.query(
            'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", "kikobaInvolved") VALUES (' +
              userID +
              ",'Invitation.','You have been invited to join " +
              results.rows[0].kikobaName +
              " kikoba.'," +
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

      res.json(true);
    }
  );
}

module.exports = { inviteUserToJoinKikoba };

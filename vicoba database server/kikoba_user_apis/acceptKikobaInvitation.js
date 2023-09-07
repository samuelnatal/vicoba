const { pool } = require("../database");

function acceptKikobaInvitation(req, res) {
  var {
    userID,
    kikobaID,
    userFirstName,
    userLastName,
    kikobaName,
    kikobaAdmin,
    notificationID,
  } = req.body;

  //Query to update invited status to approved by user.
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

      //Query to save new notification for notifying admin of the invitation accepted.
      pool.query(
        'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", notifier, "kikobaInvolved") VALUES (' +
          kikobaAdmin +
          ",'Kikoba request.','" +
          userFirstName +
          " " +
          userLastName +
          ' has accepted to be the member of your kikoba " ' +
          kikobaName +
          " \"'," +
          userID +
          "," +
          kikobaID +
          ");",
        (error, results) => {
          if (error) {
            throw error;
          }

          //Delete invitation notification after user accepted it.
          pool.query(
            'DELETE FROM "notification" WHERE "notificationID" =' +
              notificationID +
              ";",
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

module.exports = { acceptKikobaInvitation };

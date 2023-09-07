const { pool } = require("../database");

function buyShare(req, res) {
  var {
    userID,
    memberID,
    kikobaID,
    kikobaName,
    updatedWallet,
    shareAmount,
  } = req.body;

  //Query to enter a share record after user bought share.
  pool.query(
    'INSERT INTO "share" ( "memberKey", "shareAmount", "kikobaKey") VALUES ('+memberID+","+shareAmount+","+kikobaID+');',
    (error, results) => {
      if (error) {
        throw error;
      }

      //Query to update kikoba wallet after being incremented with the bought share.
      pool.query(
        'UPDATE "kikoba" SET "kikobaWallet"  ='+updatedWallet+' WHERE "kikobaID" = ' +
          kikobaID +
          ";",
        (error, results) => {
          if (error) {
            throw error;
          }

          //Query to notify a user of his loan request being rejected.
          pool.query(
            'INSERT INTO "notification" (notified, "notificationTitle", "notificationBody", notifier, "kikobaInvolved") VALUES (' +
              userID +
              ",'Ununuzi wa hisa.','Umefanikiwa kununua hisa " +
              shareAmount +
              ' katika kikoba " ' +
              kikobaName +
              ' ".\',' +
              userID +
              "," +
              kikobaID +
              ");",
            (error, results) => {
              if (error) {
                throw error;
              }
            }
          );
          res.json(true);
        }
      );
    }
  );
}

module.exports = { buyShare };

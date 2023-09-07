const { pool } = require("../database");

function payDebt(req, res) {
  var {
    userID,
    debtID,
    kikobaID,
    kikobaName,
    updatedWallet,
    debtAmount,
  } = req.body;

  console.log(req.body);

   //Query to update kikoba wallet after being incremented with the bought share.
   pool.query(
    'UPDATE "debt" SET "debtStatus"  =\'Limelipwa\' WHERE "debtID" = ' +
      debtID +
      ";",
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
              ",'Deni la mkopo.','Umefanikiwa kulipa deni la shilingi " +
              debtAmount +
              ' Tsh kama marejesho ya mkopo ulioomba katika kikoba " ' +
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

module.exports = { payDebt };

const { pool } = require("../database");

function updateKikobaProfile(req, res) {
  var {
    kikobaID,
    kikobaName,
    kikobaDesc,
    kikobaLocationID,
    sharePrice,
    maxShare,
    shareCircle,
  } = req.body;

  //Query to retrieve default values if some field are empty or not modified by a user.
  pool.query(
    'SELECT "kikobaName", "kikobaDesc", "kikobaLocationID", "sharePrice", "maxShare", "shareCircle" FROM "kikoba"  WHERE "kikobaID" = ' +
      kikobaID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }

      var kName =
        kikobaName !== undefined ? kikobaName : results.rows[0].kikobaName;
      var kDesc =
        kikobaDesc !== undefined ? kikobaDesc : results.rows[0].kikobaDesc;
      var kLoc =
        kikobaLocationID !== undefined
          ? kikobaLocationID
          : results.rows[0].kikobaLocationID.toString();

      var kSharePrice =
        sharePrice !== undefined ? sharePrice : results.rows[0].sharePrice;

      var kMaxShare =
        maxShare !== undefined ? maxShare : results.rows[0].maxShare;

      var kShareCircle =
        shareCircle !== undefined ? shareCircle : results.rows[0].shareCircle;

      //Query to update details of the existing kikoba.

      pool.query(
        'UPDATE "kikoba" SET "kikobaName"= \'' +
          kName +
          "',\"kikobaDesc\"= '" +
          kDesc +
          '\', "kikobaLocationID" =' +
          parseInt(kLoc.substring(0, 1)) +
          ',"sharePrice" =' +
          parseFloat(kSharePrice) +
          ', "maxShare" =' +
          parseInt(kMaxShare) +
          ', "shareCircle" =\'' +
          kShareCircle +
          '\' WHERE "kikobaID" =' +
          kikobaID +
          ";",
        (error, results) => {
          if (error) {
            throw error;
          }
          res.json(true);
        }
      );
    }
  );
}

module.exports = { updateKikobaProfile };

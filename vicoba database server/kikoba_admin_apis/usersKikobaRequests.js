const { pool } = require("../database");

function getUsersKikobaRequests(req, res) {
  var { kikobaID } = req.body;
  pool.query(
    'SELECT "firstName", "lastName", phone, email, gender, dob, region, district, ward, "userKey", "kikobaKey" FROM "address" JOIN "user" ON "addressID" = "addressKey" JOIN "member" ON "userID" = "userKey" WHERE status = \'Pending\' AND "kikobaKey" =' +
      kikobaID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows);
    }
  );
}

module.exports = { getUsersKikobaRequests };

import React from "react";

export default function IsPublicBadge({ isPublic }) {
  return isPublic ? (
    <span className="badge badge-success font-bold">PUBLIC</span>
  ) : (
    <span className="badge badge-error font-bold">PRIVATE</span>
  );
}

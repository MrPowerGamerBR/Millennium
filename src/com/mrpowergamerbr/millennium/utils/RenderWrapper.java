package com.mrpowergamerbr.millennium.utils;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import java.util.Map;

public class RenderWrapper {
   public Map<String, Object> context;
   public PebbleTemplate pebble;

   public RenderWrapper(PebbleTemplate pebble, Map<String, Object> context) {
      this.context = context;
      this.pebble = pebble;
   }
}

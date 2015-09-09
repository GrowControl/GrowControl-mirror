package com.growcontrol.common.meta;

import com.poixson.commonjava.xEvents.xEventListener;


public interface MetaListener extends xEventListener {


	public void onMetaEvent(final MetaEvent event);


}
